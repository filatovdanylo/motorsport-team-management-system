import { apiFetch } from '../../../shared/api/apiClient.js'

export function getAllDrivers() {
  return apiFetch('/api/drivers')
}

export function getDriverById(id) {
  return apiFetch(`/api/drivers/${id}`)
}

export function createDriver(driver) {
  return apiFetch('/api/drivers', { method: 'POST', body: driver })
}

export function updateDriver(id, driver) {
  return apiFetch(`/api/drivers/${id}`, { method: 'PUT', body: driver })
}

export function deleteDriver(id) {
  return apiFetch(`/api/drivers/${id}`, { method: 'DELETE' })
}
