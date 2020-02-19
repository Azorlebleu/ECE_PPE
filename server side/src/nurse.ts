import { LevelDB } from './leveldb'
import WriteStream from 'level-ws'

import bcrypt from 'bcrypt';

export class Nurse {
  public ID: number
  public ID_P: number[]
  public NAME: string
  public PASSWORD: string

  constructor(id: number, id_p: number[], name: string, password: string) {
    this.ID = id
    this.ID_P = id_p
    this.NAME = name
    this.PASSWORD = password
  }
}

export class NurseHandler {
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


  //N1, N2, N3,...
  public save(key: number, nurse: any, callback: (error: Error | null, result?: []) => void) {

    const stream = WriteStream(this.db)

    stream.on('error', callback)
    stream.on('close', callback)
    console.log(nurse, key)
    delete nurse.id_nurse;
    console.log(nurse, key)
    let myjson = {
      key: `N${key}`,
      value: nurse
    }
    stream.write(myjson)
    stream.end()
  }

  public getMetricsUser(key: String, callback: (error: Error | null, result?: Nurse[]) => void) {

    //creates a read stream
    const stream = this.db.createReadStream()
    let met: Nurse[]
    stream.on('error', callback)
      .on('data', (data: any) => {

        //for each data, we will fire this function
        const [_, k, timestamp] = data.key.split(":")
        const value = data.value
        if (key == k) {

          //met.push(new Nurse(timestamp, value))
        }
      })
      .on('end', (err: Error) => {
        callback(null, met)
      })
  }

  public saveUser(params: any, callback: (error: Error | null, result?: any) => void) {
    console.log("Creating a new user with params ", params)
    const stream = WriteStream(this.db)
    //stream.on('end', callback(null, { ok: ok }))
    stream.write({ key: `user:${params.username}`, value: { email: `${params.email}`, password: `${params.password}` } })
    stream.on('error')
    stream.end()
  }

  public get(key: string, callback: (err: Error | null, result?: Nurse[]) => void) {
    //creates a read stream

    const stream = this.db.createReadStream()
    var met: Nurse[] = []

    stream.on('error', callback)
      .on('data', (data: any) => {

        //for each data, we will fire this function
        const [_, k, timestamp] = data.key.split(":")
        const value = data.value
        if (key == k) {
          // met.push(new Metric(timestamp, value))
        }
      })
      .on('end', (err: Error) => {
        callback(null, met)
      })
  }

  public delete(id_nurse: number, callback: (err: Error | null) => void) {

    const stream = this.db.createReadStream()
    stream.on('error', callback)
      .on('data', (data: any) => {
        let key = "N" + id_nurse;
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
      })
  }
}
