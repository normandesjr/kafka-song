<template>
  <div v-if="winners.length">
    <ul>
      <li v-for="(winner, index) in winners" :key="index">
        <Winner :twitterName="winner.name" :twitterTime="winner.time" :twitterPosition="index"/>
      </li>
    </ul>
  </div>
  <div class="no-winners" v-else>
    <img src="../assets/sad-cloud.svg">
    <span class="placeholder-message">THERE IS NO WINNERS YET</span>
  </div>
</template>

<script>
import firebase from "../Firebase";
import moment from "moment";
import Winner from "./Winner";

export default {
  name: "Winners",
  components: {
    Winner
  },
  data() {
    return {
      winners: [],
      ref: firebase.firestore().collection("winners")
    };
  },
  created() {
    this.ref.onSnapshot(querySnapshot => {
      this.winners = [];
      querySnapshot.forEach(doc => {
        this.winners.push({
          name: doc.data().name,
          time: moment(parseInt(doc.data().time)).format("HH:MM:SS DD/MM/YYYY")
        });
      });
      this.winners.sort((a, b) => (a.time > b.time ? 1 : -1));
    });
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.no-winners {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  padding: 50px;
}
.placeholder-message {
  font-size: 30px;
  font-weight: bold;
  padding: 5%;
  color: #00b0df;
}
li {
  justify-content: center;
  font-size: 60px;
  list-style-type: none;
  display: flex;
}

ul {
  margin: 0 0 20px 0;
  padding: 0;
}
</style>
