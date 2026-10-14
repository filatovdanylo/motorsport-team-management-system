import './App.css'
import { Navigate, Route, Routes } from 'react-router'
import DriverPage from './features/drivers/DriverPage.jsx'

function App() {
  return (
      <div className="main-container">
        <Routes>
          <Route path="/" element={<Navigate to="/drivers" replace />} />
          <Route path="/drivers" element={<DriverPage />} />
        </Routes>
      </div>
  )
}

export default App
