import './App.css'
import { Route, Routes } from 'react-router'
import DriverPage from './features/drivers/DriverPage.jsx'

function App() {
  return (
      <div className="main-container">
        <Routes>
          <Route index element={<DriverPage />} />
          <Route path="/drivers" element={<DriverPage />} />
        </Routes>
      </div>
  )
}

export default App
