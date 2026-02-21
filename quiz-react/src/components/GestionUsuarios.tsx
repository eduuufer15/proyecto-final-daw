import React, { useState, useEffect } from 'react';
import '../styles/GestionUsuarios.css';

interface Usuario {
  id?: number;
  username: string;
  email?: string;
  password?: string;
  rol: 'ADMIN' | 'USER';
  enabled: boolean;
}

const API_URL = 'http://localhost:8080/api/usuarios';

const GestionUsuarios: React.FC = () => {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [mensaje, setMensaje] = useState<{ texto: string; tipo: 'success' | 'error' } | null>(null);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [usuarioEditar, setUsuarioEditar] = useState<Usuario | null>(null);
  const [buscar, setBuscar] = useState('');
  const [form, setForm] = useState<Usuario>({
    username: '',
    email: '',
    password: '',
    rol: 'USER',
    enabled: true,
  });

  useEffect(() => {
    cargarUsuarios();
  }, []);

  useEffect(() => {
    if (usuarioEditar) {
      setForm({
        username: usuarioEditar.username,
        email: usuarioEditar.email || '',
        password: '',
        rol: usuarioEditar.rol,
        enabled: usuarioEditar.enabled,
      });
    } else {
      setForm({
        username: '',
        email: '',
        password: '',
        rol: 'USER',
        enabled: true,
      });
    }
  }, [usuarioEditar]);

  const cargarUsuarios = async () => {
    try {
      setCargando(true);
      const res = await fetch(API_URL);
      if (!res.ok) throw new Error('Error al cargar usuarios');
      const data = await res.json();
      setUsuarios(data);
      setError(null);
    } catch (err) {
      setError('❌ No se pudo conectar con Spring Boot. ¿Está arrancado?');
    } finally {
      setCargando(false);
    }
  };

  const mostrarMensaje = (texto: string, tipo: 'success' | 'error' = 'success') => {
    setMensaje({ texto, tipo });
    setTimeout(() => setMensaje(null), 3000);
  };

  const guardarUsuario = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const url = usuarioEditar ? `${API_URL}/${usuarioEditar.id}` : API_URL;
      const method = usuarioEditar ? 'PUT' : 'POST';
      const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(errorText);
      }
      mostrarMensaje(usuarioEditar ? '✅ Usuario actualizado' : '✅ Usuario creado');
      setMostrarForm(false);
      setUsuarioEditar(null);
      cargarUsuarios();
    } catch (err: any) {
      mostrarMensaje('❌ Error: ' + err.message, 'error');
    }
  };

  const eliminarUsuario = async (id: number, username: string) => {
    if (!window.confirm(`¿Eliminar al usuario "${username}"?`)) return;
    try {
      const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('No se pudo eliminar');
      mostrarMensaje('✅ Usuario eliminado');
      cargarUsuarios();
    } catch (err) {
      mostrarMensaje('❌ Error al eliminar', 'error');
    }
  };

  const usuariosFiltrados = usuarios.filter(u =>
    u.username.toLowerCase().includes(buscar.toLowerCase()) ||
    (u.email && u.email.toLowerCase().includes(buscar.toLowerCase()))
  );

  return (
    <div className="gestion-usuarios">
      <h2>👥 Gestión de Usuarios</h2>

      {mensaje && (
        <div className={`mensaje ${mensaje.tipo}`}>
          {mensaje.texto}
        </div>
      )}

      <div className="acciones">
        <input
          type="text"
          placeholder="🔍 Buscar por username o email..."
          value={buscar}
          onChange={e => setBuscar(e.target.value)}
          className="input-buscar"
        />
        <button className="btn btn-primary" onClick={() => { setUsuarioEditar(null); setMostrarForm(true); }}>
          ➕ Nuevo Usuario
        </button>
        <button className="btn btn-secondary" onClick={cargarUsuarios}>
          🔄 Actualizar
        </button>
      </div>

      <div className="resumen">
        <div className="resumen-card">
          <span className="resumen-num">{usuarios.length}</span>
          <span className="resumen-label">Total</span>
        </div>
        <div className="resumen-card">
          <span className="resumen-num">{usuarios.filter(u => u.rol === 'ADMIN').length}</span>
          <span className="resumen-label">Admins</span>
        </div>
        <div className="resumen-card">
          <span className="resumen-num">{usuarios.filter(u => u.rol === 'USER').length}</span>
          <span className="resumen-label">Users</span>
        </div>
        <div className="resumen-card">
          <span className="resumen-num">{usuarios.filter(u => u.enabled).length}</span>
          <span className="resumen-label">Activos</span>
        </div>
      </div>

      {cargando && <div className="cargando">⏳ Cargando...</div>}
      {error && <div className="mensaje error">{error}</div>}

      {!cargando && !error && (
        <table className="tabla">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Rol</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {usuariosFiltrados.length === 0 ? (
              <tr><td colSpan={6} className="sin-datos">No hay usuarios</td></tr>
            ) : (
              usuariosFiltrados.map(u => (
                <tr key={u.id}>
                  <td>{u.id}</td>
                  <td><strong>{u.username}</strong></td>
                  <td>{u.email || '—'}</td>
                  <td>
                    <span className={`badge ${u.rol === 'ADMIN' ? 'badge-admin' : 'badge-user'}`}>
                      {u.rol === 'ADMIN' ? '👑 ADMIN' : '👤 USER'}
                    </span>
                  </td>
                  <td>
                    <span className={`badge ${u.enabled ? 'badge-activo' : 'badge-inactivo'}`}>
                      {u.enabled ? '✅' : '❌'}
                    </span>
                  </td>
                  <td className="acciones-tabla">
                    <button className="btn btn-warning btn-sm"
                      onClick={() => { setUsuarioEditar(u); setMostrarForm(true); }}>
                      ✏️
                    </button>
                    <button className="btn btn-danger btn-sm"
                      onClick={() => eliminarUsuario(u.id!, u.username)}>
                      🗑️
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      )}

      {mostrarForm && (
        <div className="modal-overlay">
          <div className="modal-box">
            <h3>{usuarioEditar ? '✏️ Editar Usuario' : '➕ Nuevo Usuario'}</h3>
            <form onSubmit={guardarUsuario}>
              <div className="form-group">
                <label>Username *</label>
                <input type="text" value={form.username}
                  onChange={e => setForm({ ...form, username: e.target.value })}
                  required disabled={!!usuarioEditar} />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input type="email" value={form.email}
                  onChange={e => setForm({ ...form, email: e.target.value })} />
              </div>
              <div className="form-group">
                <label>{usuarioEditar ? 'Nueva Contraseña (vacío = no cambiar)' : 'Contraseña *'}</label>
                <input type="password" value={form.password}
                  onChange={e => setForm({ ...form, password: e.target.value })}
                  required={!usuarioEditar} />
              </div>
              <div className="form-group">
                <label>Rol</label>
                <select value={form.rol} onChange={e => setForm({ ...form, rol: e.target.value as 'ADMIN' | 'USER' })}>
                  <option value="USER">👤 USER</option>
                  <option value="ADMIN">👑 ADMIN</option>
                </select>
              </div>
              <div className="form-group-check">
                <input type="checkbox" checked={form.enabled}
                  onChange={e => setForm({ ...form, enabled: e.target.checked })} id="enabled" />
                <label htmlFor="enabled">Cuenta activa</label>
              </div>
              <div className="modal-buttons">
                <button type="submit" className="btn btn-success">
                  💾 {usuarioEditar ? 'Actualizar' : 'Crear'}
                </button>
                <button type="button" className="btn btn-secondary" onClick={() => { setMostrarForm(false); setUsuarioEditar(null); }}>
                  ❌ Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default GestionUsuarios;