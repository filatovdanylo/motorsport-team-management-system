import { queryOptions } from '@tanstack/react-query'
import { apiFetch } from '../../../shared/api/apiClient.js'

export function getDriverRaceResults(driverId) {
  const params = new URLSearchParams({ driverId })
  return apiFetch(`/api/race-results?${params}`)
}

export const driverRaceResultsQueryOptions = (driverId) =>
    queryOptions({
      queryKey: ['race-results', { driverId: String(driverId) }],
      queryFn: () => getDriverRaceResults(driverId),
    })
