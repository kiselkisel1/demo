import Vue from 'vue'
 import VueI18n from 'vue-i18n'
 import Locales from './vue-i18n-locales.js';

 Vue.use(VueI18n)

function getIndex(list, id) {
  for (var i = 0; i < list.length; i++) {
    if (list[i].id == id) {
      return i;
    }
  }
  return -1;
}

var personApi = Vue.resource('/person{/id}');

Vue.component('person-form', {
  props: ['persons', 'personAttr'],
  data: function () {
    return {
      name: '',
      surname: '',
      age:'',
      id: ''
    }
  },
  watch: {
    personAttr: function (newVal, oldVal) {
      this.id = newVal.id;
      this.name = newVal.name;
      this.surname = newVal.surname;
      this.age=newVal.age;
    }
  },
  template:
    '<div class="container">' +
    '<div>' +
    '<label>{{ $t("app.name") }}</label>' +
    '<input type="name" placeholder="Input name" v-model="name" />' +
    '</div>'+
    '<label>Surname: </label>' +
    '<input type="surname" placeholder="Input surname" v-model="surname" />' +
    '<div>' +
    '<label>Age: </label>' +
    '<input type="age" placeholder="Input age" v-model="age" />' +
    '</div>'+
    '<input type="button" value="Save" @click="save" />' +
    '</div>',
  methods: {
    save: function () {
      var person = { name: this.name, surname: this.surname, age: this.age };

      if (this.id) {
        personApi.update({ id: this.id }, person).then(result =>
          result.json().then(data => {
            var index = getIndex(this.persons, data.id);
            this.persons.splice(index, 1, data);
            this.name = '',
            this.surname='',
            this.age='',
            this.id = ''
          })
        )
      }
      else {
        personApi.save({}, person).then(result =>
          result.json().then(data => {
            this.persons.push(data);
            this.name = '',
            this.surname='',
            this.age=''
          })
        )
      }
    }
  }
}
);

Vue.component('person-row', {
  props: ['person', 'edit-method', 'persons'],
  template: '<div>' +
    ' {{person.id}} {{ person.name }} {{person.surname}} {{person.age}}' +
    '<span style="position: absolute;right: 0">' +
    '<input type="button" value="Edit" @click="edit"/>' +
    '<input type="button" value="Delete" @click="del"/>' +
    '</span>' +
    '</div>',
  methods: {
    edit: function () {
      this.editMethod(this.person);
    },
    del: function () {
      personApi.remove({ id: this.person.id }).then(result => {
        if (result.ok) {
          this.persons.splice(this.persons.indexOf(this.person), 1);
        }
      }
      )
    }
  }
}
);

Vue.component('persons-list', {
  props: ['persons'],
  data: function () {
    return {
      person: null
    }
  },
  template: '<div style="position:relative;width:300px">' +
    '<person-form :persons="persons" :personAttr="person" />' +
    '<person-row v-for="person in persons" :key="person.id" :person="person" :editMethod="editMethod" :persons="persons"/>' +
    '</div>',
  created: function () {
    personApi.get().then(result =>
      result.json().then(data =>
        data.forEach(person => this.persons.push(person))
      )
    )
  },
  methods: {
    editMethod: function (person) {
      this.person = person;
    }
  }
});

var app = new Vue({
  el: '#app',
  template: '<persons-list :persons="persons"/>',
  data: {
    persons: []
  }
});