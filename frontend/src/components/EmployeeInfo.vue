<template>
    <div>
        <div>
            <button v-on:click.prevent="returnToList"> Go Back </button>
            <div class="employee2">
                <div id="card-header2">
                    <div id="name2">
                        {{employee.firstName}} {{employee.lastName}}
                    </div>
                    <div id="title2">
                        {{employee.role}}
                        <div v-if="employee.businessUnit != null">
                            {{employee.businessUnit}}
                        </div>
                        <div v-if="employee.assignedTo != null">
                            Assigned to: {{employee.assignedTo}}
                        </div>
                    </div>
                </div>
                <div id="space2"><br></div>
                <div id="dates2">
                    Birthday: {{employee.birthDate}}<br>
                    Date of hire: {{employee.hiredDate}}
                </div>
                <div id="emails2">
                    Contact email: {{employee.contactEmail}}<br>
                    Company email: {{employee.companyEmail}}
                </div>
                <div id="address2">
                    {{employee.address.street}}<br>
                    <div v-if="employee.address.suite != null">
                        {{employee.address.suite}}
                    </div>
                    {{employee.address.city}}, {{employee.address.region}} {{employee.address.postal}}, {{employee.address.country}}
                </div>
                <div id="buttons2">
                    <button v-on:click.prevent="edit"> Edit </button>
                </div>

                <div id="space3"><br></div>

                <div id="skills2">
                    <div id="skills-header">
                        Employee Skills
                    </div><br>
                    <div id="add-skill-button">
                        <button v-on:click.prevent=""> Add skill </button>
                    </div><br>
                    <div id="skills">
                        <div v-for="skill in skills" :key="skill.id" class="skill">
                            {{skill.name}}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            employee: {
                id: "",
                firstName: "",
                lastName: "",
                address: {
                    id: "",
                    street: "",
                    suite: "",
                    city: "",
                    region: "",
                    postal: "",
                    country: ""
                },
                contactEmail: "",
                companyEmail: "",
                birthDate: "",
                hiredDate: "",
                role: "",
                businessUnit: " ",
                assignedTo: " "
            },

            skills: []
        }
    },
    props: {
        employeeId: String
    },
    methods: {
        getEmployeeFromId() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.employeeId}`,
                {
                    method: 'GET',
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    }
                }
            )
            .then ( (response) => {return response.json()})
            .then ( (returnedEmployee) => {
                this.employee = returnedEmployee;
                this.getEmployeeFromId();
            })
            .catch( (err) => {console.log(err)});
        },

        getEmployeeSkills() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.employeeId}/skills`,
                {
                    method: 'GET',
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json"
                    }
                }
            )
            .then ( (response) => {return response.json()})
            .then ( (skillsData) => {
                this.skills = skillsData;
            })
            .catch( (err) => {console.log(err)});
        },

        returnToList() {
            this.$emit('returnToList');
        },

        edit() {
            this.$emit('showEditEmployee', this.employee);
        }
    },
    created() {
        this.getEmployeeFromId();
    }
}
</script>

<style scoped>
    .employee2 {
        margin: 8px 8px 8px 8px;
        padding: 8px 8px 8px 8px;
        border: 2px solid black;
        display: grid;
        grid-template-columns: 1fr 15fr 1fr 15fr 1fr;
        grid-template-areas: 
            ". header2 . emails2 ."
            "space2 space2 space2 space2 space2"
            ". address2 . dates2 ."
            ". . . buttons2 ."
            "space3 space3 space3 space3 space3"
            ". skills2 skills2 skills2 .";

    }

    #card-header2 {
        display: inline-block;
        grid-area: header2;
    }

    #name2 {
        display: inline-block;
        text-align: left;
        font-weight: bold;
        font-size: 32px;
        grid-area: name2;
    }

    #title2 {
        font-style: italic;
        grid-area: title2;
    }

    #space2 {
        grid-area: space2;
    }

    #space3 {
        grid-area: space3;
    }

    #dates2 {
        display: inline-block;
        grid-area: dates2;
    }

    #emails2 {
        display: inline-block;
        grid-area: emails2;
    }

    #address2 {
        display: inline-block;
        grid-area: address2;
    }

    #buttons2 {
        grid-area: buttons2;
        text-align: right;
    }

    #skills2 {
        text-align: left;
        grid-area: skills2;
    }

    #skills2 > #skills-header {
        font-weight: bold;
        font-size: 24px;
    }

    #skills {
        border: 2px solid black;
        padding: 8px 8px 8px 8px;
    }

    .skill {
        border: 1px solid black;
    }

</style>