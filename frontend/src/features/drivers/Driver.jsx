import { Link } from 'react-router'
import { formatLabel } from '../../shared/format.js'

function Driver({ driver, onDelete, deleting }) {
  return (
      <li className="driver">
        <span className="driver-number">#{driver.number}</span>
        <Link to={`/drivers/${driver.id}`} className="driver-name">
          {driver.firstName} {driver.lastName}
        </Link>
        <span className="driver-meta">{driver.nationality}</span>
        <span className={`driver-status driver-status--${driver.status.toLowerCase()}`}>
          {formatLabel(driver.status)}
        </span>
        <span className="driver-actions">
          <Link to={`/drivers/${driver.id}/edit`} className="btn btn--small">Edit</Link>
          <button
              type="button"
              className="btn btn--small btn--danger"
              onClick={() => onDelete(driver)}
              disabled={deleting}
          >
            {deleting ? 'Deleting...' : 'Delete'}
          </button>
        </span>
      </li>
  )
}

export default Driver
