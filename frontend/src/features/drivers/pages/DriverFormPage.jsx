import { Link, useNavigate, useParams } from 'react-router'
import { useQuery } from '@tanstack/react-query'
import DriverForm from '../DriverForm.jsx'
import { toFormValues } from '../driverForm.js'
import { driverQueryOptions } from '../api/driversQueries.js'
import { useCreateDriver, useUpdateDriver } from '../api/driversMutations.js'
import '../Drivers.css'

function DriverFormPage() {
  const { id } = useParams()
  const isEdit = id !== undefined
  const navigate = useNavigate()

  const driverQuery = useQuery({ ...driverQueryOptions(id), enabled: isEdit })
  const createMutation = useCreateDriver()
  const updateMutation = useUpdateDriver(id)
  const mutation = isEdit ? updateMutation : createMutation

  const backPath = isEdit ? `/drivers/${id}` : '/drivers'

  const handleSubmit = async (payload) => {
    const saved = await mutation.mutateAsync(payload)
    navigate(`/drivers/${saved.id}`)
  }

  let content
  if (isEdit && driverQuery.isPending) {
    content = <p className="message">Loading...</p>
  } else if (isEdit && driverQuery.isError) {
    content = <p className="message message--error">Error: {driverQuery.error.message}</p>
  } else {
    content = (
        <DriverForm
            key={driverQuery.data?.id ?? 'new'}
            initialValues={isEdit ? toFormValues(driverQuery.data) : undefined}
            submitLabel={isEdit ? 'Save changes' : 'Create driver'}
            onSubmit={handleSubmit}
            onCancel={() => navigate(backPath)}
        />
    )
  }

  return (
      <div className="driver-form-page">
        <Link to={backPath}>← Back</Link>
        <div className="page-header">
          <h1>{isEdit ? 'Edit driver' : 'Add driver'}</h1>
        </div>
        {content}
      </div>
  )
}

export default DriverFormPage
