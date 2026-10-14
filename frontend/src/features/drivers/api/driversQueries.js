import { queryOptions } from '@tanstack/react-query'
import { getAllDrivers, getDriverById } from './drivers.js'

export const driverKeys = {
  all: ['drivers'],
  list: () => [...driverKeys.all, 'list'],
  detail: (id) => [...driverKeys.all, 'detail', String(id)],
}

export const driversQueryOptions = () =>
    queryOptions({
      queryKey: driverKeys.list(),
      queryFn: () => getAllDrivers(),
      staleTime: 30_000,
    })

export const driverQueryOptions = (id) =>
    queryOptions({
      queryKey: driverKeys.detail(id),
      queryFn: () => getDriverById(id),
    })
