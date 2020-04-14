<template>
  <div id="edit-skill-form">
        <form>
            <div class="form-input">
                <label for="fieldname">Field name: </label>
                <input type="text" id="fieldname" name="fieldname" placeholder="e.x. 'Java'" v-model="skill.field.name">
            </div>
            <div class="form-input">
                <label for="fieldtype">Field type: </label>
                <input type="text" id="fieldtype" name="fieldtype" placeholder="e.x. 'Software Development'" v-model="skill.field.type">
            </div>
            <div class="form-input">
                <label for="exp">Months of experience: </label>
                <input type="number" id="exp" name="exp" min="0" placeholder="# months" v-model="skill.experience">
            </div>
            <div class="form-input">
                <label for="sum">Summary: </label>
                <textarea id="sum" name="sum" rows="5" cols="50" placeholder="Describe your experience..." v-model="skill.summary"></textarea>
            </div><br>

            <button v-on:click.prevent="updateSkillWithId"> Update </button>
            <button v-on:click.prevent="deleteSkillWithId"> Delete </button>
            <button v-on:click.prevent="cancel"> Cancel </button>
        </form>
    </div>
</template>

<script>
export default {
    props: {
        initial: String,
        anEmployee: String
    },

    data() {
        return {
            skill: {
                id: "",
                field: {
                    id: "",
                    name: "",
                    type: "",
                },
                experience: 0,
                summary: ""
            }
        }
    },

    methods: {
        cancel() {
            this.$emit("returnToEmployee");
        },

        getSkillWithId() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.anEmployee}/skills/${this.initial}`,
                {
                    method: 'GET',
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    }
                }
            )
            .then ( (response) => {return response.json()})
            .then ( (returnedSkill) => {
                this.skill = returnedSkill;
            })
            .catch( (err) => {
                console.log(err)});
        },

        updateSkillWithId() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.anEmployee}/skills/${this.initial}`,
                {
                    method: 'PUT',
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.skill)
                }
            )
            .then ( (response) => { if (response.ok) {
                this.cancel();
            }})
            .catch ( (err) => {console.error(err)})
        },

        deleteSkillWithId() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.anEmployee}/skills/${this.initial}`,
                {
                    method: 'DELETE',
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.skill)
                }
            )
            .then ( (response) => { if (response.ok) {
                this.cancel();
            }})
            .catch ( (err) => {console.error(err)})
        }
    },

    created() {
        this.getSkillWithId();
    }
}
</script>

<style>

</style>