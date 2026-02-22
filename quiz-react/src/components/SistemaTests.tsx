import React, { useState, useEffect } from 'react';
import '../styles/SistemaTests.css';

// ── INTERFACES ──────────────────────────────────────

interface Opcion {
  id: number;
  textoOpcion: string;
  esCorrecta: boolean;
}

interface Pregunta {
  id: number;
  textoPregunta: string;
  tipo: 'verdadero_falso' | 'seleccion_unica' | 'seleccion_multiple';
  respuestaCorrecta?: boolean;
  opciones?: Opcion[];
}

interface ResultadoTest {
  correctas: number;
  incorrectas: number;
  total: number;
  nota: number;
  porcentaje: number;
}

const API_URL = 'http://localhost:8080/api/preguntas';
const RESULTADOS_URL = 'http://localhost:8080/api/resultados';

const SistemaTests: React.FC = () => {
  const [preguntas, setPreguntas] = useState<Pregunta[]>([]);
  const [respuestas, setRespuestas] = useState<Map<number, any>>(new Map());
  const [resultado, setResultado] = useState<ResultadoTest | null>(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [testIniciado, setTestIniciado] = useState(false);
  const [usuarioNombre, setUsuarioNombre] = useState('');

  useEffect(() => {
    cargarPreguntas();
  }, []);

  const cargarPreguntas = async () => {
    try {
      setCargando(true);
      const res = await fetch(API_URL);
      if (!res.ok) throw new Error('Error al cargar preguntas');
      const data = await res.json();
      setPreguntas(data);
      setError(null);
    } catch (err) {
      setError('❌ No se pudo conectar con Spring Boot');
    } finally {
      setCargando(false);
    }
  };

  const iniciarTest = () => {

    // Pedir nombre del usuario antes de empezar

    const nombre = prompt('Introduce tu nombre:');
    if (!nombre || nombre.trim() === '') {
      alert('Debes introducir tu nombre para realizar el test');
      return;
    }
    setUsuarioNombre(nombre.trim());
    setTestIniciado(true);
    setRespuestas(new Map());
    setResultado(null);
  };

  const handleRespuesta = (preguntaId: number, respuesta: any) => {
    const nuevasRespuestas = new Map(respuestas);
    nuevasRespuestas.set(preguntaId, respuesta);
    setRespuestas(nuevasRespuestas);
  };

  const guardarResultado = async (resultado: ResultadoTest) => {
    try {
      const response = await fetch(RESULTADOS_URL, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          usuarioNombre: usuarioNombre,
          totalPreguntas: resultado.total,
          correctas: resultado.correctas,
          incorrectas: resultado.incorrectas,
          nota: resultado.nota,
          porcentaje: resultado.porcentaje
        })
      });

      if (response.ok) {
        console.log('✅ Resultado guardado en el servidor');
      } else {
        console.error('❌ Error al guardar resultado');
      }
    } catch (error) {
      console.error('❌ Error de red al guardar resultado:', error);
    }
  };

  const calcularNota = async () => {
    let correctas = 0;
    let incorrectas = 0;

    preguntas.forEach(p => {
      const respuestaUsuario = respuestas.get(p.id);
      if (!respuestaUsuario && respuestaUsuario !== false) {
        incorrectas++;
        return;
      }

      if (p.tipo === 'verdadero_falso') {
        if (respuestaUsuario === p.respuestaCorrecta) correctas++;
        else incorrectas++;
      } else if (p.tipo === 'seleccion_unica') {
        const opcionCorrecta = p.opciones?.find(o => o.esCorrecta);
        if (respuestaUsuario === opcionCorrecta?.id) correctas++;
        else incorrectas++;
      } else if (p.tipo === 'seleccion_multiple') {
        const correctasIds = p.opciones?.filter(o => o.esCorrecta).map(o => o.id) || [];
        const respuestasArray = respuestaUsuario as number[];
        const esCorrecta = correctasIds.length === respuestasArray.length &&
          correctasIds.every(id => respuestasArray.includes(id));
        if (esCorrecta) correctas++;
        else incorrectas++;
      }
    });

    const total = preguntas.length;
    const porcentaje = (correctas / total) * 100;
    const nota = (correctas / total) * 10;

    const resultadoFinal = { correctas, incorrectas, total, nota, porcentaje };
    setResultado(resultadoFinal);

    // Guardar resultado en el backend

    await guardarResultado(resultadoFinal);
  };

  const reiniciarTest = () => {
    setTestIniciado(false);
    setRespuestas(new Map());
    setResultado(null);
    setUsuarioNombre('');
  };

  if (cargando) return <div className="cargando">⏳ Cargando preguntas...</div>;
  if (error) return <div className="mensaje error">{error}</div>;
  if (preguntas.length === 0) return <div className="mensaje error">⚠️ No hay preguntas disponibles</div>;

  // PANTALLA INICIAL

  if (!testIniciado && !resultado) {
    return (
      <div className="sistema-tests">
        <div className="test-inicio">
          <h2>🎯 Sistema de Tests</h2>
          <div className="test-info">
            <p>📝 <strong>{preguntas.length} preguntas</strong> disponibles</p>
            <p>⏱️ Sin límite de tiempo</p>
            <p>🎓 Nota mínima para aprobar: <strong>5.0</strong></p>
          </div>
          <button className="btn btn-primary btn-lg" onClick={iniciarTest}>
            🚀 Iniciar Test
          </button>
        </div>
      </div>
    );
  }

  // PANTALLA DE RESULTADO

  if (resultado) {
    const aprobado = resultado.nota >= 5;
    return (
      <div className="sistema-tests">
        <div className={`test-resultado ${aprobado ? 'aprobado' : 'suspenso'}`}>
          <h2>{aprobado ? '🎉 ¡Aprobado!' : '😢 Suspenso'}</h2>
          <p className="usuario-nombre">Usuario: <strong>{usuarioNombre}</strong></p>
          <div className="resultado-nota">
            <span className="nota-numero">{resultado.nota.toFixed(2)}</span>
            <span className="nota-texto">/ 10</span>
          </div>
          <div className="resultado-detalles">
            <div className="detalle">
              <span className="detalle-num">{resultado.correctas}</span>
              <span className="detalle-label">✅ Correctas</span>
            </div>
            <div className="detalle">
              <span className="detalle-num">{resultado.incorrectas}</span>
              <span className="detalle-label">❌ Incorrectas</span>
            </div>
            <div className="detalle">
              <span className="detalle-num">{resultado.porcentaje.toFixed(0)}%</span>
              <span className="detalle-label">📊 Acierto</span>
            </div>
          </div>
          <div className="alert alert-info" style={{marginTop: '20px', padding: '10px', background: '#d1ecf1', borderRadius: '8px'}}>
            ✅ Tu resultado ha sido guardado en el sistema
          </div>
          <button className="btn btn-primary" onClick={reiniciarTest}>
            🔄 Hacer otro test
          </button>
        </div>
      </div>
    );
  }

  // PANTALLA DE TEST EN CURSO

  return (
    <div className="sistema-tests">
      <div className="test-header">
        <h2>📝 Test en curso</h2>
        <p>Usuario: <strong>{usuarioNombre}</strong></p>
      </div>

      <div className="preguntas-lista">
        {preguntas.map((p, idx) => (
          <div key={p.id} className="pregunta-card">
            <h3>
              <span className="pregunta-num">{idx + 1}.</span> {p.textoPregunta}
            </h3>

            {/* VERDADERO/FALSO */}

            {p.tipo === 'verdadero_falso' && (
              <div className="opciones-vf">
                <button
                  className={`opcion-vf ${respuestas.get(p.id) === true ? 'seleccionada' : ''}`}
                  onClick={() => handleRespuesta(p.id, true)}>
                  ✅ Verdadero
                </button>
                <button
                  className={`opcion-vf ${respuestas.get(p.id) === false ? 'seleccionada' : ''}`}
                  onClick={() => handleRespuesta(p.id, false)}>
                  ❌ Falso
                </button>
              </div>
            )}

            {/* SELECCIÓN ÚNICA */}

            {p.tipo === 'seleccion_unica' && p.opciones && (
              <div className="opciones-lista">
                {p.opciones.map(opcion => (
                  <label key={opcion.id} className="opcion-radio">
                    <input
                      type="radio"
                      name={`pregunta-${p.id}`}
                      checked={respuestas.get(p.id) === opcion.id}
                      onChange={() => handleRespuesta(p.id, opcion.id)}
                    />
                    <span>{opcion.textoOpcion}</span>
                  </label>
                ))}
              </div>
            )}

            {/* SELECCIÓN MÚLTIPLE */}
            
            {p.tipo === 'seleccion_multiple' && p.opciones && (
              <div className="opciones-lista">
                {p.opciones.map(opcion => {
                  const seleccionadas = (respuestas.get(p.id) as number[]) || [];
                  return (
                    <label key={opcion.id} className="opcion-check">
                      <input
                        type="checkbox"
                        checked={seleccionadas.includes(opcion.id)}
                        onChange={(e) => {
                          if (e.target.checked) {
                            handleRespuesta(p.id, [...seleccionadas, opcion.id]);
                          } else {
                            handleRespuesta(p.id, seleccionadas.filter(id => id !== opcion.id));
                          }
                        }}
                      />
                      <span>{opcion.textoOpcion}</span>
                    </label>
                  );
                })}
              </div>
            )}
          </div>
        ))}
      </div>

      <div className="test-footer">
        <p>Respondidas: {respuestas.size} / {preguntas.length}</p>
        <button
          className="btn btn-success btn-lg"
          onClick={calcularNota}
          disabled={respuestas.size === 0}>
          📊 Ver Nota Final
        </button>
      </div>
    </div>
  );
};

export default SistemaTests;