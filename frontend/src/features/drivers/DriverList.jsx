import Driver from './Driver.jsx'

function DriverList({ drivers, loading, error }) {
  if (loading) return <p className="driver-message">Loading...</p>
  if (error) return <p className="driver-message driver-message--error">Error: {error.message}</p>
  if (drivers.length === 0) return <p className="driver-message">No drivers yet.</p>

  return (
      <ul className="driver-list">
        {drivers.map((d) => <Driver key={d.id} driver={d} />)}
      </ul>
  )
}

export default DriverList
