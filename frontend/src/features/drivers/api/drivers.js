async function toApiError(res, fallbackMessage) {
  let body = null;
  try {
    body = await res.json();
  } catch {
    // No body
  }

  const error = new Error(body?.detail ?? `${fallbackMessage} (${res.status})`);
  error.fieldErrors = body?.errors ?? {};
  return error;
}

export async function getAllDrivers() {
  const res = await fetch('/api/drivers');
  if (!res.ok) throw await toApiError(res, 'Failed to load drivers');
  return res.json();
}

export async function createDriver(driver) {
  const res = await fetch('/api/drivers', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(driver),
  });
  if (!res.ok) throw await toApiError(res, 'Failed to create driver');
  return res.json();
}
