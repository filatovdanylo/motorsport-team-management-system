import { queryOptions } from '@tanstack/react-query'
import { apiFetch } from '../../../shared/api/apiClient.js'

export function getDriverContracts(driverId) {
  const params = new URLSearchParams({ driverId })
  return apiFetch(`/api/driver-contracts?${params}`)
}

export const driverContractsQueryOptions = (driverId) =>
    queryOptions({
      queryKey: ['driver-contracts', { driverId: String(driverId) }],
      queryFn: () => getDriverContracts(driverId),
    })
