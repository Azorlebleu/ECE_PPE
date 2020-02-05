

export class PPatient {
  public ID: number
  public ID_PP: number[]
  public NAME: string
  public AGE: number
  public ADDRESS: string
  public Phone_Number: number
  public Notes: string
  public Time_Request: string

  constructor(id: number, id_pp: number[], name: string, age: number, address: string, phone_number: number, notes: string, time_request: string) {
    this.ID = id
    this.ID_PP = id_pp
    this.NAME = name
    this.AGE = age
    this.ADDRESS = address
    this.Phone_Number = phone_number
    this.Notes = notes
    this.Time_Request = time_request
  }
}
