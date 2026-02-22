import { useState } from 'react';
import GestionUsuarios from './components/GestionUsuarios';
import SistemaTests from './components/SistemaTests';
import './App.css';

function App() {
  const [pestanaActiva, setPestanaActiva] = useState<'usuarios' | 'tests'>('tests');

  return (
    <div className="app">
      <header className="app-header">
        <h1>🎯 Quiz App</h1>
        <p>Gestión de preguntas y usuarios</p>
      </header>

      <nav className="app-nav">
        <button
          className={`nav-btn ${pestanaActiva === 'tests' ? 'activa' : ''}`}
          onClick={() => setPestanaActiva('tests')}>
          📝 Sistema de Tests
        </button>
        <button
          className={`nav-btn ${pestanaActiva === 'usuarios' ? 'activa' : ''}`}
          onClick={() => setPestanaActiva('usuarios')}>
          👥 Gestión de Usuarios
        </button>
      </nav>

      <main className="app-main">
        {pestanaActiva === 'tests' && <SistemaTests />}
        {pestanaActiva === 'usuarios' && <GestionUsuarios />}
      </main>

      <footer className="app-footer">
        <p>© 2026 Quiz App — Proyecto PMDM + AD</p>
      </footer>
    </div>
  );
}

export default App;