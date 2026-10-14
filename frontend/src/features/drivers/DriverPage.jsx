import { useEffect, useState } from 'react'
import { createDriver, getAllDrivers } from './api/drivers.js'
import DriverForm from './DriverForm.jsx'
import DriverList from './DriverList.jsx'
import './Drivers.css'

function DriverPage() {
  const [drivers, setDrivers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    let ignore = false

    getAllDrivers()
        .then((data) => {
          if (!ignore) setDrivers(data)
        })
        .catch((e) => {
          if (!ignore) setError(e)
        })
        .finally(() => {
          if (!ignore) setLoading(false)
        })

    return () => {
      ignore = true
    }
  }, [])

  const handleCreate = async (driver) => {
    const created = await createDriver(driver)
    setDrivers((prev) => [...prev, created])
  }

  return (
      <div className="driver-page">
        <h1>Drivers</h1>
        <DriverForm onSubmit={handleCreate} />
        <DriverList drivers={drivers} loading={loading} error={error} />
      </div>
  )
}

export default DriverPage
