const moneyFormatter = new Intl.NumberFormat(undefined, {
  style: 'currency',
  currency: 'USD',
  maximumFractionDigits: 0,
})

export function formatMoney(value) {
  return value == null ? '—' : moneyFormatter.format(value)
}

export function formatDuration(ms) {
  if (ms == null) return '—'

  const hours = Math.floor(ms / 3_600_000)
  const minutes = Math.floor((ms % 3_600_000) / 60_000)
  const seconds = Math.floor((ms % 60_000) / 1000)
  const millis = ms % 1000

  const ss = String(seconds).padStart(2, '0')
  const mmm = String(millis).padStart(3, '0')

  if (hours > 0) {
    return `${hours}:${String(minutes).padStart(2, '0')}:${ss}.${mmm}`
  }
  return `${minutes}:${ss}.${mmm}`
}

export function formatLabel(enumValue) {
  if (!enumValue) return '—'
  const text = enumValue.replaceAll('_', ' ').toLowerCase()
  return text.charAt(0).toUpperCase() + text.slice(1)
}
