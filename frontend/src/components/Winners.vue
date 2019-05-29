<template>
  <div>
    <ul>
      <li v-for="(name, index) in winners" :key="index">
        <Winner :twitterName="name.name" />
      </li>
    </ul>
  </div>
</template>

<script>
import firebase from "../Firebase"
import Winner from "./Winner"

export default {
  name: 'Winners',
  components: {
    Winner
  },
  data () {
    return {
      winners: [],
      winnersCollection: firebase.firestore().collection('winners')
    }
  },
  created () {
    this.winnersCollection.onSnapshot((querySnapshot) => {
      this.winners = [];
      querySnapshot.forEach((doc) => {
        this.winners.push({
          name: doc.data().name
        })
      })
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
li {
  justify-content: center;
  font-size: 60px;
  list-style-type: none;
  display: flex;
}
ul {
  margin: 0;
  padding: 0
}
</style>
