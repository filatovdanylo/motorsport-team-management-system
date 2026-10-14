import { formatLabel, formatMoney } from '../../shared/format.js'

function DriverContractList({ contracts, loading, error }) {
  if (loading) return <p className="message">Loading contracts...</p>
  if (error) return <p className="message message--error">Error: {error.message}</p>
  if (contracts.length === 0) return <p className="message">No contracts.</p>

  return (
      <table className="data-table">
        <thead>
          <tr>
            <th>Season</th>
            <th>Team</th>
            <th>Role</th>
            <th>Period</th>
            <th className="num">Salary</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {contracts.map((c) => (
              <tr key={c.id}>
                <td>{c.season.name}</td>
                <td>{c.team.name}</td>
                <td>{formatLabel(c.driverRole)}</td>
                <td>{c.contractStart} – {c.contractEnd}</td>
                <td className="num">{formatMoney(c.salary)}</td>
                <td>{formatLabel(c.status)}</td>
              </tr>
          ))}
        </tbody>
      </table>
  )
}

export default DriverContractList
