import { formatDuration, formatLabel } from '../../shared/format.js'

function RaceResultList({ results, loading, error }) {
  if (loading) return <p className="message">Loading race results...</p>
  if (error) return <p className="message message--error">Error: {error.message}</p>
  if (results.length === 0) return <p className="message">No race results.</p>

  return (
      <table className="data-table">
        <thead>
          <tr>
            <th>Season</th>
            <th>Round</th>
            <th>Race</th>
            <th className="num">Position</th>
            <th className="num">Points</th>
            <th>Fastest lap</th>
            <th>Status</th>
            <th className="num">Time</th>
          </tr>
        </thead>
        <tbody>
          {results.map((r) => (
              <tr key={r.id}>
                <td>{r.race.season.name}</td>
                <td>{r.race.roundNumber}</td>
                <td>{r.race.name}</td>
                <td className="num">{r.position ?? '—'}</td>
                <td className="num">{r.points}</td>
                <td>{r.fastestLap ? 'Yes' : 'No'}</td>
                <td>{formatLabel(r.status)}</td>
                <td className="num">{formatDuration(r.totalRaceTimeMs)}</td>
              </tr>
          ))}
        </tbody>
      </table>
  )
}

export default RaceResultList
