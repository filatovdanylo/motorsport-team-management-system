import { Link, useNavigate, useParams } from 'react-router'
import { useQuery } from '@tanstack/react-query'
import DriverDetail from '../DriverDetail.jsx'
import DriverContractList from '../../contracts/DriverContractList.jsx'
import RaceResultList from '../../raceResults/RaceResultList.jsx'
import { driverQueryOptions } from '../api/driversQueries.js'
import { useDeleteDriver } from '../api/driversMutations.js'
import { driverContractsQueryOptions } from '../../contracts/api/driverContracts.js'
import { driverRaceResultsQueryOptions } from '../../raceResults/api/raceResults.js'
import '../Drivers.css'

function DriverDetailPage() {
  const { id } = useParams()
  const navigate = useNavigate()

  const driverQuery = useQuery(driverQueryOptions(id))
  const contractsQuery = useQuery(driverContractsQueryOptions(id))
  const resultsQuery = useQuery(driverRaceResultsQueryOptions(id))
  const deleteMutation = useDeleteDriver()

  const driver = driverQuery.data

  const handleDelete = () => {
    const name = `${driver.firstName} ${driver.lastName}`
    if (!window.confirm(`Delete driver ${name}?`)) return
    deleteMutation.mutate(driver.id, { onSuccess: () => navigate('/drivers') })
  }

  if (driverQuery.isPending) return <p className="message">Loading...</p>

  if (driverQuery.isError) {
    return (
        <div className="driver-detail-page">
          <p className="message message--error">Error: {driverQuery.error.message}</p>
          <Link to="/drivers">← Back to drivers</Link>
        </div>
    )
  }

  return (
      <div className="driver-detail-page">
        <Link to="/drivers">← Back to drivers</Link>

        <div className="page-header">
          <h1>{driver.firstName} {driver.lastName}</h1>
          <div className="page-actions">
            <Link to={`/drivers/${driver.id}/edit`} className="btn">Edit</Link>
            <button
                type="button"
                className="btn btn--danger"
                onClick={handleDelete}
                disabled={deleteMutation.isPending}
            >
              {deleteMutation.isPending ? 'Deleting...' : 'Delete'}
            </button>
          </div>
        </div>

        {deleteMutation.isError && (
            <p className="message message--error" role="alert">
              {deleteMutation.error.message}
            </p>
        )}

        <DriverDetail driver={driver} />

        <section>
          <h2>Contracts</h2>
          <DriverContractList
              contracts={contractsQuery.data ?? []}
              loading={contractsQuery.isPending}
              error={contractsQuery.error}
          />
        </section>

        <section>
          <h2>Race results</h2>
          <RaceResultList
              results={resultsQuery.data ?? []}
              loading={resultsQuery.isPending}
              error={resultsQuery.error}
          />
        </section>
      </div>
  )
}

export default DriverDetailPage
