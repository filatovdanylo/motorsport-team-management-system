import { formatLabel } from '../../shared/format.js'

function DriverDetail({ driver }) {
  return (
      <dl className="detail-grid">
        <dt>Number</dt>
        <dd>#{driver.number}</dd>

        <dt>First name</dt>
        <dd>{driver.firstName}</dd>

        <dt>Last name</dt>
        <dd>{driver.lastName}</dd>

        <dt>Nationality</dt>
        <dd>{driver.nationality}</dd>

        <dt>Date of birth</dt>
        <dd>{driver.dateOfBirth ?? '—'}</dd>

        <dt>Status</dt>
        <dd>{formatLabel(driver.status)}</dd>
      </dl>
  )
}

export default DriverDetail
