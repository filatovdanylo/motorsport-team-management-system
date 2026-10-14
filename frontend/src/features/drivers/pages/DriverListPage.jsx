import { Link } from 'react-router'
import { useQuery } from '@tanstack/react-query'
import DriverList from '../DriverList.jsx'
import { driversQueryOptions } from '../api/driversQueries.js'
import { useDeleteDriver } from '../api/driversMutations.js'
import '../Drivers.css'

function DriverListPage() {
  const { data: drivers = [], isPending, error } = useQuery(driversQueryOptions())
  const deleteMutation = useDeleteDriver()

  const handleDelete = (driver) => {
    const name = `${driver.firstName} ${driver.lastName}`
    if (!window.confirm(`Delete driver ${name}?`)) return
    deleteMutation.mutate(driver.id)
  }


  const deletingId = deleteMutation.isPending ? deleteMutation.variables : null

  return (
      <div className="driver-page">
        <div className="page-header">
          <h1>Drivers</h1>
          <Link to="/drivers/new" className="btn btn--primary">Add driver</Link>
        </div>

        {deleteMutation.isError && (
            <p className="message message--error" role="alert">
              {deleteMutation.error.message}
            </p>
        )}

        <DriverList
            drivers={drivers}
            loading={isPending}
            error={error}
            onDelete={handleDelete}
            deletingId={deletingId}
        />
      </div>
  )
}

export default DriverListPage
