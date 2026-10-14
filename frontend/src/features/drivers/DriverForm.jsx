import { useState } from 'react'

const initialState = {
  firstName: '',
  lastName: '',
  nationality: '',
  dateOfBirth: '',
  number: '',
  status: 'ACTIVE',
}

function FieldError({ message }) {
  return message ? <small className="field-error">{message}</small> : null
}

function DriverForm({ onSubmit }) {
  const [form, setForm] = useState(initialState)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)
  const [fieldErrors, setFieldErrors] = useState({})

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSubmitting(true)
    setError(null)
    setFieldErrors({})

    try {
      await onSubmit({
        firstName: form.firstName.trim(),
        lastName: form.lastName.trim(),
        nationality: form.nationality.trim(),
        dateOfBirth: form.dateOfBirth || null,
        number: form.number === '' ? null : Number(form.number),
        status: form.status,
      })
      setForm(initialState)
    } catch (err) {
      setError(err.message)
      setFieldErrors(err.fieldErrors ?? {})
    } finally {
      setSubmitting(false)
    }
  }

  return (
      <form className="driver-form" onSubmit={handleSubmit}>
        <label>
          First name
          <input
              type="text"
              name="firstName"
              maxLength={100}
              value={form.firstName}
              onChange={handleChange}
              required
          />
          <FieldError message={fieldErrors.firstName} />
        </label>

        <label>
          Last name
          <input
              type="text"
              name="lastName"
              maxLength={100}
              value={form.lastName}
              onChange={handleChange}
              required
          />
          <FieldError message={fieldErrors.lastName} />
        </label>

        <label>
          Nationality
          <input
              type="text"
              name="nationality"
              maxLength={100}
              value={form.nationality}
              onChange={handleChange}
              required
          />
          <FieldError message={fieldErrors.nationality} />
        </label>

        <label>
          Date of birth
          <input
              type="date"
              name="dateOfBirth"
              value={form.dateOfBirth}
              onChange={handleChange}
          />
          <FieldError message={fieldErrors.dateOfBirth} />
        </label>

        <label>
          Number
          <input
              type="number"
              name="number"
              min="0"
              max="999"
              value={form.number}
              onChange={handleChange}
              required
          />
          <FieldError message={fieldErrors.number} />
        </label>

        <label>
          Status
          <select name="status" value={form.status} onChange={handleChange}>
            <option value="ACTIVE">Active</option>
            <option value="INACTIVE">Inactive</option>
            <option value="RETIRED">Retired</option>
          </select>
          <FieldError message={fieldErrors.status} />
        </label>

        {error && <p className="form-error">{error}</p>}

        <button type="submit" disabled={submitting}>
          {submitting ? 'Creating...' : 'Create driver'}
        </button>
      </form>
  )
}

export default DriverForm
