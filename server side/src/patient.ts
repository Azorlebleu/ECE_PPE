import { LevelDB } from './leveldb'
import WriteStream from 'level-ws'

export class Patient {
  public ID: number
  public ID_P: number[]
  public NAME: string
  public AGE: number
  public ADDRESS: string
  public Phone_Number: number
  public Notes: string
  public Time_Request: string

  constructor(id: number, id_p: number[], name: string, age: number, address: string, phone_number: number, notes: string, time_request: string) {
    this.ID = id
    this.ID_P = id_p
    this.NAME = name
    this.AGE = age
    this.ADDRESS = address
    this.Phone_Number = phone_number
    this.Notes = notes
    this.Time_Request = time_request
  }
}

export class PatientHandler {
  public db: any

  constructor(dbPath: string) {
    this.db = LevelDB.open(dbPath)
  }

  public see_all(callback: (error: Error | null, result?: any[]) => void) {
    const stream = this.db.createReadStream()

    var arr: any[] = []

    stream.on('error', callback)
      .on('data', (data: any) => {
        arr.push(data)
      })
      .on('end', (err: Error) => {
        callback(null, arr)
      })
  }
  public save(id_nurse: number, id_patient: number, patient: Patient, callback: (error: Error | null, result?: []) => void) {

    const stream = WriteStream(this.db)

    stream.on('error', callback)
    stream.on('close', callback)
    console.log(patient)
    stream.write({ key: `PAT_N${id_nurse}_ID${id_patient}`, value: patient })

    stream.end()
  }

  public delete(id_nurse: number, id_patient: number, callback: (err: Error | null) => void) {
    let key = "PAT_N" + id_nurse + "_ID" + id_patient;
    console.log("Trying to delete " + key)
    const stream = this.db.createReadStream()
    stream.on('error', callback)
      .on('data', (data: any) => {
        if (data.key = key) {
          this.db.del(key)
        }
      })
      .on('end', (err: Error) => {
        callback(null)
      })
  }
}