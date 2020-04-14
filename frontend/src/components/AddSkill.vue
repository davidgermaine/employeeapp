<template>
    <div id="add-skill-form">
        <form>
            <div class="form-input">
                <label for="fieldname">Field name: </label>
                <input type="text" id="fieldname" name="fieldname" placeholder="e.x. 'Java'" v-model="field.name">
            </div>
            <div class="form-input">
                <label for="fieldtype">Field type: </label>
                <input type="text" id="fieldtype" name="fieldtype" placeholder="e.x. 'Software Development'" v-model="field.type">
            </div>
            <div class="form-input">
                <label for="exp">Months of experience: </label>
                <input type="number" id="exp" name="exp" min="0" placeholder="# months" v-model="skill.experience">
            </div>
            <div class="form-input">
                <label for="sum">Summary: </label>
                <textarea id="sum" name="sum" rows="5" cols="50" placeholder="Describe your experience..." v-model="skill.summary"></textarea>
            </div><br>

            <button v-on:click.prevent="addSkillToEmployee"> Submit </button>
            <button v-on:click.prevent="cancel"> Cancel </button>
        </form>
    </div>
</template>

<script>
export default {
    props: {
        employeeId: String
    },

    data() {
        return {
            skill: {
                field: {},
                experience: 0,
                summary: ""
            },
            field: {
                name: "",
                type: ""
            }
        }
    },

    methods: {
        cancel() {
            this.$emit('returnToEmployee');
        },

        addSkillToEmployee() {
            this.skill.field = this.field;

            fetch(`http://localhost:8080/employeeapp/employees/${this.employeeId}/skills`,
                {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.skill)
                }
            )
            .then ( (response) => {
                if(response.ok) {
                    this.cancel();
                }
            })
            .catch ( (err) => {
                console.error(err)
            })
        }
    }

}
</script>

<style>
    #add-skill-form {
        display: inline-block;
        border: 2px solid black;
        padding: 8px 8px 8px 8px;
    }

    .form-input {
        text-align: top;
    }

    textarea {
        max-width: 500px;
        resize: horizontal;
    }
</style>