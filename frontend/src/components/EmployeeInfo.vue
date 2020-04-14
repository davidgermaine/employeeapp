<template>
    <div>
        <div>
            <div id="button-margin">
                <button v-on:click.prevent="returnToList"> Go Back </button>
            </div>
            <div class="employee2">
                <div id="name2">
                    {{employee.firstName}} {{employee.lastName}}
                </div>
                <div id="title2">
                    {{employee.role}}
                    <div v-if="employee.businessUnit != null">
                        {{employee.businessUnit}}
                    </div>
                    <div v-if="employee.assignedTo != null">
                        Assigned to: {{assignedToEmployee.firstName}} {{assignedToEmployee.lastName}}
                    </div>
                </div>

                <div id="space2"><br></div>

                <div id="dates2">
                    <div class="mini-header"> Extra Info </div>
                    Birthday: {{employee.birthDate}}<br>
                    Date of hire: {{employee.hiredDate}}
                </div>
                <div id="emails2">
                    <div class="mini-header"> Contact Info </div>
                    Contact email: {{employee.contactEmail}}<br>
                    Company email: {{employee.companyEmail}}
                </div>
                <div id="address2">
                    <div class="mini-header"> Mailing Address </div>
                    {{employee.address.street}}<br>
                    <div v-if="employee.address.suite != null">
                        {{employee.address.suite}}
                    </div>
                    {{employee.address.city}}, {{employee.address.region}} {{employee.address.postal}}, {{employee.address.country}}
                </div>
                <div id="buttons2">
                    <br><button v-on:click.prevent="edit"> Edit Employee </button>
                </div>

                <div id="space3"><br></div>

                <div id="skills2">
                    <div class="header">
                        Employee Skills
                    </div><br>
                    <div id="add-skill-button" v-if="!shouldShowEditSkillForm">
                        <button v-on:click.prevent="showAddSkillForm"> Add skill </button>
                    </div><br>
                    <div id="skills" v-if="shouldShowSkills">
                        <div v-for="skill in skills" :key="skill.id">
                            <SkillInfo :skill="skill" @editThisSkill="showEditSkillForm"/>
                        </div>
                    </div>
                    <div v-if="shouldShowAddSkillForm">
                        <AddSkill v-on:returnToEmployee="showEmployeeInfo" v-bind:employeeId="employee.id"/>
                    </div>
                    <div v-if="shouldShowEditSkillForm">
                        <EditSkill v-bind:initial="selectedSkillId" v-bind:anEmployee="selectedEmployeeId" @returnToEmployee="showEmployeeInfo"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import AddSkill from '@/components/AddSkill.vue'
import SkillInfo from '@/components/SkillInfo.vue'
import EditSkill from '@/components/EditSkill.vue'

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
            assignedToEmployee: {},
            skills: [],
            selectedSkillId: "",
            selectedEmployeeId: "",
            shouldShowSkills: true,
            shouldShowAddSkillForm: false,
            shouldShowEditSkillForm: false,
        }
    },
    props: {
        employeeId: String
    },
    methods: {
        showEmployeeInfo() {
            this.shouldShowSkills = true;
            this.shouldShowAddSkillForm = false;
            this.shouldShowEditSkillForm = false;
            this.getEmployeeSkills();
        },

        showAddSkillForm() {
            this.shouldShowSkills = false;
            this.shouldShowAddSkillForm = true;
            this.shouldShowEditSkillForm = false;
        },

        showEditSkillForm(skillId) {
            this.shouldShowSkills = false;
            this.shouldShowAddSkillForm = false;
            this.shouldShowEditSkillForm = true;
            this.selectedSkillId = skillId;
            this.selectedEmployeeId = this.employeeId;
        },

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
                this.getEmployeeSkills();
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
                this.getAssignedTo();
            })
            .catch( (err) => {console.log(err)});
        },

        getAssignedTo() {
            fetch(`http://localhost:8080/employeeapp/employees/${this.employee.assignedTo}`,
                {
                    method: 'GET',
                    headers: {
                        Accept: "application/json",
                        "Content-Tpye": "application/json"
                    }
                }
            )
            .then ( (response) => {return response.json()})
            .then ( (returnedEmployee) => {
            this.assignedToEmployee = returnedEmployee;
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
    },
    components: {
        AddSkill,
        EditSkill,
        SkillInfo
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
            ". name2 name2 name2 ."
            ". title2 . emails2 ."
            "space2 space2 space2 space2 space2"
            ". address2 . dates2 ."
            ". buttons2 . . ."
            "space3 space3 space3 space3 space3"
            ". skills2 skills2 skills2 .";

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
        text-align: left;
    }

    #skills2 {
        text-align: left;
        grid-area: skills2;
    }

    .header {
        font-weight: bold;
        font-size: 24px;
    }

    .mini-header {
        font-weight: bold;
        font-size: 20px;
    }

</style>