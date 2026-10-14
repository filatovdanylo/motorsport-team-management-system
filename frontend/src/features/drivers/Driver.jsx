function Driver({ driver }) {
  return (
      <li className="driver">
        <span className="driver-number">#{driver.number}</span>
        <span className="driver-name">{driver.firstName} {driver.lastName}</span>
        <span className="driver-meta">{driver.nationality}</span>
        <span className={`driver-status driver-status--${driver.status.toLowerCase()}`}>
          {driver.status}
        </span>
      </li>
  )
}

export default Driver
