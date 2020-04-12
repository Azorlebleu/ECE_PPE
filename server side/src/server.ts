import express = require('express')

import { MetricsHandler, Metric } from './metrics'
import path = require('path')
import bodyparser = require('body-parser')
import { ok } from 'assert'
import { UserHandler, User } from './users'
import { cpus } from 'os'
import session = require('express-session')
import levelSession = require('level-session-store')
import { Nurse, NurseHandler } from './nurse'
import { PatientHandler } from './patient'
import { runScript } from './pyscrip'
var cookieParser = require('cookie-parser')
const app = express()
const port: string = process.env.PORT || '8080'

app.use(express.static(path.join(__dirname, '/../public')))
app.use(bodyparser.json())
app.use(bodyparser.urlencoded({ extended: true }))
app.use(cookieParser())

app.set('views', __dirname + "/../views")
app.set('view engine', 'ejs');
const LevelStore = levelSession(session)

app.use(session({
  secret: 'my very VERY secret phrase',
  store: new LevelStore('./db/sessions'),
  resave: true,
  saveUninitialized: true
}))
const dbNurse: NurseHandler = new NurseHandler('./db/nurses')
const dbPatient: PatientHandler = new PatientHandler('./db/patient')


//API PART

app.get('/api/all-nurses', (req: any, res: any) => {
  dbNurse.see_all((err: Error | null, result?: any) => {
    if (err) throw err
    res.json(result)
  })
})

app.get('/api/all-patients', (req: any, res: any) => {
  dbPatient.see_all((err: Error | null, result?: any) => {
    if (err) throw err
    res.json(result)
  })
})

app.get('/TEST', (req: any, res: any) => {
  res.status(200).send(({"ok":[1,2]}))
})
//add new nurse
/*
{
  "id_nurse": 1,
  "id_p": [ 1, 2, 3 ],
  "name": "maman",
  "password": "lolilol123"
}
*/

app.get('/api/my-journey', (req: any, res: any) => {

  var my_journey = [
    {
      "name":"Jean",
      "surname":"Dupond",
      "time":"8h30",
      "address":"4 rue de la mare Jouane Saint Arnoult en Yvelines France"
    },
    {
      "name":"Anouk",
      "surname":"Costa",
      "time":"9h00",
      "address":"15 rue André Thome Sonchamp France"
    },
    {
      "name":"Alice",
      "surname":"Simon",
      "time":"9h30",
      "address":"26 rue des paradis Saint Arnoult en Yvelines France"
    }
  ]
  res.status(200).send(my_journey)
});



app.post('/api/new-nurse/', (req: any, res: any) => {
  console.log("Saving nurse :", req.body)
  dbNurse.save(req.body.id_nurse, req.body, (err: Error | null) => {
    if (err) {
      res.status(500).send("error")
      console.log(err)
    }
    res.status(200).send("ok")
  })
})



//delete a nurse
/*
{
	"id_nurse":1
}
*/
app.delete('/api/delete-nurse/', (req: any, res: any) => {

  dbNurse.delete(req.body.id_nurse, (err) => {
    if (err) {
      res.status(500).send("error")
      console.log(err)
    }
    res.status(200).send("ok")
  })
})


//add new patient
/*
{
	"id_nurse":2,
	"id_patient":1,
  "name":"loulou",
  "address": "Queretaro",
  "number": "07171717",
  "notes": ""
}

*/
app.post('/api/new-patient/', (req: any, res: any) => {
  console.log("Saving patient :", req.body)
  dbPatient.save(req.body.id_nurse, req.body.id_patient, req.body, (err: Error | null) => {
    if (err) res.status(500).send({"error": "error"});
    res.status(200).send({"ok": "Patient saved succesfully"})
  })
})

//delete one patient
/*
{
"id_nurse":"2",
"id_patient": "1"
}
*/
app.delete('/api/delete-patient/', (req: any, res: any) => {

  dbPatient.delete(req.body.id_nurse, req.body.id_patient, (err) => {
    runScript();
    if (err) {
      res.status(500).send("error")
      console.log(err)
    }
    res.status(200).send("ok")
  })
})


//start the server
app.listen(port, (err: Error) => {
  if (err) throw err
  console.log(`Server is running on http://localhost:${port}`)
})
