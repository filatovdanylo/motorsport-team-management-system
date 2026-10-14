export class ApiError extends Error {
  constructor(message, status, fieldErrors = {}) {
    super(message)
    this.name = 'ApiError'
    this.status = status
    this.fieldErrors = fieldErrors
  }
}

async function toApiError(res) {
  let body = null
  try {
    body = await res.json()
  } catch {
    // no JSON body
  }

  return new ApiError(
      body?.detail ?? `Request failed (${res.status})`,
      res.status,
      body?.errors ?? {},
  )
}

export async function apiFetch(url, { method = 'GET', body } = {}) {
  const hasBody = body !== undefined

  const res = await fetch(url, {
    method,
    headers: hasBody ? { 'Content-Type': 'application/json' } : undefined,
    body: hasBody ? JSON.stringify(body) : undefined,
  })

  if (!res.ok) throw await toApiError(res)
  if (res.status === 204) return null
  return res.json()
}
