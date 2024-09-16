/*
  Sample user data

  Roles
  0: patient
  1: vaccination clinic
  2: organisation

*/

const users = [
  {
    id: 'chris@ibm.com',
    fname: 'Hemsworth',
    sname: 'Chris',
    bday: '10131962',
    age: '38',
    gender: 'Male',
    patientID: '558111',
    phoneNumber: '+61 491 570 157',
    address: '55 West 81st Street, Upper West Side',
    password: 'chris',
    role: 0,
  },
  {
    id: 'hubert@ibm.com',
    fname: 'Hartan',
    sname: 'Hubert',
    password: 'hubert',
    role: 1,
  },
  {
    id: 'trideep@ibm.com',
    fname: 'Trideep',
    sname: 'Trideep',
    password: 'trideep',
    role: 2,
  },
];

export default users;
