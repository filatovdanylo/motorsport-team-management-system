import Driver from './Driver.jsx'

function DriverList({ drivers, loading, error, onDelete, deletingId }) {
  if (loading) return <p className="message">Loading...</p>
  if (error) return <p className="message message--error">Error: {error.message}</p>
  if (drivers.length === 0) return <p className="message">No drivers yet.</p>

  return (
      <ul className="driver-list">
        {drivers.map((d) => (
            <Driver
                key={d.id}
                driver={d}
                onDelete={onDelete}
                deleting={deletingId === d.id}
            />
        ))}
      </ul>
  )
}

export default DriverList
