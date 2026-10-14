import { useState } from 'react'
import {
  driverStatuses,
  emptyDriverForm,
  toPayload,
  validateDriver,
} from './driverForm.js'

function FieldError({ message }) {
  return message ? <small className="field-error">{message}</small> : null
}

function DriverForm({ initialValues = emptyDriverForm, submitLabel, onSubmit, onCancel }) {
  const [form, setForm] = useState(initialValues)
  const [errors, setErrors] = useState({})
  const [formError, setFormError] = useState(null)
  const [submitting, setSubmitting] = useState(false)

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
    setErrors((prev) => ({ ...prev, [name]: undefined }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setFormError(null)

    const clientErrors = validateDriver(form)
    if (Object.keys(clientErrors).length > 0) {
      setErrors(clientErrors)
      return
    }

    setErrors({})
    setSubmitting(true)
    try {
      await onSubmit(toPayload(form))
    } catch (err) {
      // server-side validation / conflict errors
      setFormError(err.message)
      setErrors(err.fieldErrors ?? {})
    } finally {
      setSubmitting(false)
    }
  }

  return (
      <form className="driver-form" onSubmit={handleSubmit} noValidate>
        <label>
          First name
          <input
              type="text"
              name="firstName"
              maxLength={100}
              value={form.firstName}
              onChange={handleChange}
              aria-invalid={Boolean(errors.firstName)}
          />
          <FieldError message={errors.firstName} />
        </label>

        <label>
          Last name
          <input
              type="text"
              name="lastName"
              maxLength={100}
              value={form.lastName}
              onChange={handleChange}
              aria-invalid={Boolean(errors.lastName)}
          />
          <FieldError message={errors.lastName} />
        </label>

        <label>
          Nationality
          <input
              type="text"
              name="nationality"
              maxLength={100}
              value={form.nationality}
              onChange={handleChange}
              aria-invalid={Boolean(errors.nationality)}
          />
          <FieldError message={errors.nationality} />
        </label>

        <label>
          Date of birth
          <input
              type="date"
              name="dateOfBirth"
              value={form.dateOfBirth}
              onChange={handleChange}
              aria-invalid={Boolean(errors.dateOfBirth)}
          />
          <FieldError message={errors.dateOfBirth} />
        </label>

        <label>
          Number
          <input
              type="number"
              name="number"
              min="0"
              max="999"
              step="1"
              value={form.number}
              onChange={handleChange}
              aria-invalid={Boolean(errors.number)}
          />
          <FieldError message={errors.number} />
        </label>

        <label>
          Status
          <select
              name="status"
              value={form.status}
              onChange={handleChange}
              aria-invalid={Boolean(errors.status)}
          >
            {driverStatuses.map((s) => (
                <option key={s.value} value={s.value}>{s.label}</option>
            ))}
          </select>
          <FieldError message={errors.status} />
        </label>

        {formError && <p className="form-error">{formError}</p>}

        <div className="form-actions">
          <button type="submit" className="btn btn--primary" disabled={submitting}>
            {submitting ? 'Saving...' : submitLabel}
          </button>
          <button type="button" className="btn" onClick={onCancel} disabled={submitting}>
            Cancel
          </button>
        </div>
      </form>
  )
}

export default DriverForm
