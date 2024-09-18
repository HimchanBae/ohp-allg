/*
Sample AllergyIntolerance Data
*/

const allergyIntoleranceData = [
  {
    resourceType: 'AllergyIntolerance',
    identifier: 'Unique ID', // Clarify what type of ID is needed ex.A00001
    clinicalStatus: 'active | inactive | resolved',
    type: 'allergy | intolerance',
    category: 'food | medication | environment | biologic',
    criticality: 'low | high | unable-to-assess',
    code: 'A code identifying the specific type, such as a particular food or medication', // It might be better to change it to 'name' or 'display'
    patient: {
      reference: 'Patient/53373',
      type: Patient,
    },
    /* 
    encounter : too complex for MVP
    onsetAge, onsetRange, onsetString : 
    can be integrated into "onsetDateTime" for a simpler structure
    */
    recordedDate: '1995-05-01', // Date when the allergy was first recorded
    onsetDateTime: '1980-05-01', // First occurrence of the allergy reaction
    lastOccurrence: '2010-05-01', // Date of the most recent known allergy reaction
    note: 'Optional Notes field', // String field for any additional notes
    reaction: {
      substance: 'Amplicillin',
      manifestation: 'Hives',
      severity: 'Moderate',
    }, // Without Codeable Concept for simplicity
  },
];
