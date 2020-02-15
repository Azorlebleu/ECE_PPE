import { LevelDB } from './leveldb'
import WriteStream from 'level-ws'

import bcrypt from 'bcrypt';

export class Nurse {
  public ID: number
  public ID_PP: number[]
  public ID_CP: number[]
  public NAME: string
  public PASSWORD: string

  constructor(id: number, id_pp: number[], id_cp: number[], name: string, password: string) {
    this.ID = id
    this.ID_PP = id_pp
    this.ID_CP = id_cp 
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
  public save(key: number, nurse: Nurse, callback: (error: Error | null, result?: []) => void) {

    const stream = WriteStream(this.db)

    stream.on('error', callback)
    stream.on('close', callback)
    console.log(nurse, key)
    delete nurse.ID;
    console.log(nurse, key)
    stream.write({ key: `N${key}`, value: nurse })

    stream.end() 
  }

  public getMetricsUser(key: String, callback: (error: Error | null, result?: Nurse[]) => void) {

    //creates a read stream
    const stream = this.db.createReadStream()
    let met : Nurse[]
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

  public delete(key: number, callback: (err: Error | null) => void) {

    const stream = this.db.createReadStream()
    stream.on('error', callback)
      .on('data', (data: any) => {

        //for each data, we will fire this function
        const [_, k, timestamp] = data.key.split(":")
        const value = data.value
        if (key != k) {
          console.log(`Data ${k} does not match key ${key} and won't be deleted`)
        } else {
          console.log(`Data ${k} match the key ${key} and will be deleted`)
          this.db.del(data.key)
        }
      })
      .on('end', (err: Error) => {
        callback(null)
      })
  }

  public deleteOne(params, callback: (err: Error | null) => void) {

    const stream = this.db.createReadStream()
    let username = params.id
    let timestamp = params.timestamp
    stream.on('error', callback)
      .on('data', (data: any) => {

        //for each data, we will fire this function
        const [_, usr, tmstmp] = data.key.split(":")
        const value = data.value
        if (usr != username || tmstmp != timestamp) {
          
        } else {
          console.log(`Timestamp ${timestamp} for user ${username} will be deleted`)
          this.db.del(data.key)
        }
      })
      .on('end', (err: Error) => {
        callback(null)
      })
  }


}
