import * as firebase from 'firebase'

const config = {
  apiKey: "",
  authDomain: "",
  databaseURL: "",
  projectId: "kafka-song-4b5bd",
  storageBucket: "",
  messagingSenderId: "",
  appId: ""
}

firebase.initializeApp(config)

firebase.firestore()

export default firebase