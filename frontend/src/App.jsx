import './App.css'
import { Navigate, Route, Routes } from 'react-router'
import DriverListPage from './features/drivers/pages/DriverListPage.jsx'
import DriverDetailPage from './features/drivers/pages/DriverDetailPage.jsx'
import DriverFormPage from './features/drivers/pages/DriverFormPage.jsx'

function App() {
  return (
      <div className="main-container">
        <Routes>
          <Route index element={<Navigate to="/drivers" replace />} />
          <Route path="/drivers" element={<DriverListPage />} />
          <Route path="/drivers/new" element={<DriverFormPage />} />
          <Route path="/drivers/:id" element={<DriverDetailPage />} />
          <Route path="/drivers/:id/edit" element={<DriverFormPage />} />
          <Route path="*" element={<p className="message">Page not found.</p>} />
        </Routes>
      </div>
  )
}

export default App
