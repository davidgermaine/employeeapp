<template>
    <div id="add-employee-form">
        <h3> Add a new employee </h3>
        
        <form id="add-employee" >
            <div class="form-input">
                <label for="fname">First name: </label>
                <input type="text" id="fname" name="fname" placeholder="enter first name" v-model="employee.firstName">
            </div>
            <div class="form-input">
                <label for="lname">Last name: </label>
                <input type="text" id="lname" name="lname" placeholder="enter last name" v-model="employee.lastName">
            </div><br>

            <div class="form-input">
                <label for="address1">Street: </label>
                <input type="text" id="address1" name="address1" v-model="address.street">
            </div>
            <div class="form-input">
                <label for="address2">Suite: </label>
                <input type="text" id="address2" name="address2" v-model="address.suite">
            </div>
            <div class="form-input">
                <label for="city">City: </label>
                <input type="text" id="city" name="city" placeholder="city" v-model="address.city">
            </div>
            <div class="form-input">
                <label for="region">State/Province: </label>
                <input type="text" id="region" name="region" placeholder="2-3 characters" v-model="address.region">
            </div>
            <div class="form-input">
                <label for="postal">Postal code: </label>
                <input type="text" id="postal" name="postal" placeholder="postal code" v-model="address.postal">
            </div>
            <div class="form-input">
                <label for="country">Country: </label>
                <input type="text" id="country" name="country" placeholder="2 character format" v-model="address.country">
            </div><br>

            <div class="form-input">
                <label for="contact">Contact email: </label>
                <input type="text" id="contact" name="contact" v-model="employee.contactEmail">
            </div>
            <div class="form-input">
                <label for="company">Company email: </label>
                <input type="text" id="company" name="company" v-model="employee.companyEmail">
            </div>
            <div class="form-input">
                <label for="birthdate">Date of birth: </label>
                <input type="date" id="birthdate" name="birthdate" v-model="employee.birthDate">
            </div>
            <div class="form-input">
                <label for="hireddate">Date of hire: </label>
                <input type="date" id="hireddate" name="hireddate" v-model="employee.hiredDate">
            </div>
            <div class="form-input">
                <label for="role">Role: </label>
                <select id="role" name="role" v-model="employee.role">
                    <option value="Technical Consultant">Technical Consultant</option>
                    <option value="Project Manager">Project Manager</option>
                    <option value="Director">Director</option>
                    <option value="Chief">Chief</option>
                </select>
            </div>
            <div class="form-input">
                <label for="business">Business Unit: </label>
                <select id="business" name="business" v-model="employee.businessUnit">
                    <option value="">N/A</option>
                    <option value="Digital Experience Group">Digital Experience Group</option>
                    <option value="Adobe">Adobe</option>
                    <option value="IBM NBU">IBM NBU</option>
                    <option value="API Management">API Management</option>
                </select>
            </div>
            <div class="form-input">
                <label for="assignedto">Assigned to: </label>
                <select id="assignedto" name="assignedto" v-model="employee.assignedTo">
                    <option value="">N/A</option>
                    <option v-for="supervisor in supervisors" v-bind:key="supervisor.id" :value="supervisor.id">
                        {{supervisor.firstName}} {{supervisor.lastName}}
                    </option>
                </select>
            </div><br>
            
            <button v-bind:disabled="!isValidForm" v-on:click.prevent="addEmployee"> Submit </button>
            <button v-on:click.prevent="cancel"> Cancel </button>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            employee: {
                firstName: "",
                lastName: "",
                address: {},
                contactEmail: "",
                companyEmail: "",
                birthDate: "",
                hiredDate: "",
                role: "",
                businessUnit: "",
                assignedTo: ""
            },
            address: {
                street: "",
                suite: "",
                city: "",
                region: "",
                postal: "",
                country: ""
            },
            supervisors: []
        }
    },
    computed: {
        isValidForm() {
            return this.employee.firstName != null && this.employee.lastName != null && this.employee.companyEmail != null 
                && this.employee.birthDate != null && this.employee.hiredDate != null && this.employee.role != null 
                && this.address.street != null && this.address.city != null && this.address.region != null 
                && this.address.postal != null && this.address.country != null;
        }
    },
    created() {
        this.employee = {};
        this.getSupervisors();
    },
    methods: {
        getSupervisors() {
            fetch('http://localhost:8080/employeeapp/employees/supervisors')
            .then ( (response) => {return response.json()})
            .then ( (supervisorData) => {
                this.supervisors = supervisorData;
            })
            .catch( (err) => {console.log(err)})
        },

        addEmployee() {
            this.employee.address = this.address;
            
            fetch("http://localhost:8080/employeeapp/employees",
                {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(this.employee)
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
        },
        
        cancel() {
            this.$emit('hideAddEmployeeForm');
        }
    }

}
</script>

<style>
    #add-employee-form {
        text-align: center;
    }
</style>