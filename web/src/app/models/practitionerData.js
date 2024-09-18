/*
Sample Practitioner Data
*/

const practitionerData = [
  {
    resourceType: 'Practitioner',
    identifier: 'john.smith@ibm.com',
    firstName: 'John',
    lastName: 'Smith',
    telecom: '04111111112',
    address: '4RPD',
    gender: 'Male',
    birthDate: '5/22/1990',
    qualification: [
      {
        display: 'Doctor of Medicine',
        period: '2010-2020',
        issuer: 'Medical Council',
      },
      {
        display: 'Advanced Cardiac Life Support (ACLS)',
        period: '2015-2025',
        issuer: 'American Heart Association',
      },
    ],
    // Qualifications, certifications, accreditations, licenses, training, etc. pertaining to the provision of care
  },
];
