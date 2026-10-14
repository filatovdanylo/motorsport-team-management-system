export const emptyDriverForm = {
  firstName: '',
  lastName: '',
  nationality: '',
  dateOfBirth: '',
  number: '',
  status: 'ACTIVE',
}

export const driverStatuses = [
  { value: 'ACTIVE', label: 'Active' },
  { value: 'INACTIVE', label: 'Inactive' },
  { value: 'RETIRED', label: 'Retired' },
]

export function toFormValues(driver) {
  return {
    firstName: driver.firstName,
    lastName: driver.lastName,
    nationality: driver.nationality,
    dateOfBirth: driver.dateOfBirth ?? '',
    number: String(driver.number),
    status: driver.status,
  }
}

export function toPayload(form) {
  return {
    firstName: form.firstName.trim(),
    lastName: form.lastName.trim(),
    nationality: form.nationality.trim(),
    dateOfBirth: form.dateOfBirth || null,
    number: Number(form.number),
    status: form.status,
  }
}

function todayIso() {
  const now = new Date()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${now.getFullYear()}-${month}-${day}`
}


export function validateDriver(form) {
  const errors = {}

  const requiredText = [
    ['firstName', 'First name'],
    ['lastName', 'Last name'],
    ['nationality', 'Nationality'],
  ]
  for (const [field, label] of requiredText) {
    const value = form[field].trim()
    if (!value) errors[field] = `${label} is required`
    else if (value.length > 100) errors[field] = `${label} must be at most 100 characters`
  }

  if (form.dateOfBirth && form.dateOfBirth >= todayIso()) {
    errors.dateOfBirth = 'Date of birth must be in the past'
  }

  const number = Number(form.number)
  if (form.number.trim() === '') {
    errors.number = 'Number is required'
  } else if (!Number.isInteger(number) || number < 0 || number > 999) {
    errors.number = 'Number must be a whole number between 0 and 999'
  }

  if (!driverStatuses.some((s) => s.value === form.status)) {
    errors.status = 'Status is required'
  }

  return errors
}
