import { useMutation, useQueryClient } from '@tanstack/react-query'
import { createDriver, deleteDriver, updateDriver } from './drivers.js'
import { driverKeys } from './driversQueries.js'

export function useCreateDriver() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: createDriver,
    onSuccess: () => queryClient.invalidateQueries({ queryKey: driverKeys.all }),
  })
}

export function useUpdateDriver(id) {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: (driver) => updateDriver(id, driver),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: driverKeys.all }),
  })
}

export function useDeleteDriver() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: deleteDriver,
    onSuccess: (_, id) => {
      queryClient.removeQueries({ queryKey: driverKeys.detail(id) })
      return queryClient.invalidateQueries({ queryKey: driverKeys.list() })
    },
  })
}
