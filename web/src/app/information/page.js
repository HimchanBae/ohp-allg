'use client';

import React from 'react';
import {
  Breadcrumb,
  BreadcrumbItem,
  Button,
  Grid,
  Column,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableHeader,
  TableBody,
  TableCell,
} from '@carbon/react';
import { Edit as EditIcon } from '@carbon/icons-react';

import Link from 'next/link';

const InformationPage = () => {
  const patientData = {
    resourceType: 'Patient',
    identifier: 'will.smith@ibm.com',
    firstName: 'Will',
    lastName: 'Smith',
    telecom: '0412-345-678',
    address: 'Room114, 4 Research Park Dr, Macquarie Park NSW 2113',
    gender: 'Male',
    birthDate: '04/22/1990',
  };

  const rows = [
    { id: '1', label: 'First Name', value: patientData.firstName },
    { id: '2', label: 'Last Name', value: patientData.lastName },
    { id: '3', label: 'Email', value: patientData.identifier },
    { id: '4', label: 'Phone', value: patientData.telecom },
    { id: '5', label: 'Address', value: patientData.address },
    { id: '6', label: 'Gender', value: patientData.gender },
    { id: '7', label: 'Birth Date', value: patientData.birthDate },
  ];

  const allergyList = [
    {
      name: 'Peanut Allergy',
      type: 'Allergy',
      category: 'Food',
      criticality: 'High',
    },
    {
      name: 'Penicillin Intolerance',
      type: 'Intolerance',
      category: 'Medication',
      criticality: 'Low',
    },
    {
      name: 'Pollen Allergy',
      type: 'Allergy',
      category: 'Environment',
      criticality: 'Unable to Assess',
    },
    {
      name: 'Cat Dander Allergy',
      type: 'Allergy',
      category: 'Biologic',
      criticality: 'High',
    },
  ];

  const headers = [
    { key: 'label', header: 'Label' },
    { key: 'value', header: 'Information' },
  ];

  const allergyHeaders = [
    { key: 'type', header: 'Allergy Type' },
    { key: 'name', header: 'Name' },
    { key: 'category', header: 'Category' },
    { key: 'criticality', header: 'Criticality' },
  ];

  const handleEdit = (allergy) => {
    console.log('Edit allergy:', allergy);
    // 여기서 알러지 수정 페이지로 이동하거나, 모달 창을 띄워 수정할 수 있습니다.
  };

  return (
    <Grid className="info-grid">
      <Column lg={16} md={8} sm={4} className="landing-page__banner">
        <Breadcrumb noTrailingSlash aria-label="Page navigation">
          <BreadcrumbItem>
            <a href="/">Home</a>
          </BreadcrumbItem>
          <BreadcrumbItem isCurrentPage>Information</BreadcrumbItem>
        </Breadcrumb>
        <h1>My Healthcare Information</h1>
      </Column>
      <Column lg={16} md={8} sm={4} className="landing-page__r2">
        <Grid>
          <Column lg={8} md={4} sm={4} className="landing-page__content_l">
            <TableContainer
              className="landing-page__user_table"
              title="User Info">
              <Table>
                <TableHead>
                  <TableRow>
                    {headers.map((header) => (
                      <TableHeader key={header.key}>
                        {header.header}
                      </TableHeader>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows.map((row) => (
                    <TableRow key={row.id}>
                      <TableCell>{row.label}</TableCell>
                      <TableCell>{row.value}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <Button className="edit-button">Edit My Information</Button>

            <TableContainer
              className="landing-page__allergy_table"
              title="Allergy List">
              <Table>
                <TableHead>
                  <TableRow>
                    {allergyHeaders.map((header) => (
                      <TableHeader key={header.key}>
                        {header.header}
                      </TableHeader>
                    ))}
                    <TableHeader></TableHeader>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {allergyList.map((allergy, index) => (
                    <TableRow key={index}>
                      <TableCell>{allergy.type}</TableCell>
                      <TableCell>{allergy.name}</TableCell>
                      <TableCell>{allergy.category}</TableCell>
                      <TableCell>{allergy.criticality}</TableCell>
                      <TableCell>
                        <Button
                          hasIconOnly
                          renderIcon={() => <EditIcon />}
                          iconDescription="Edit Allergy"
                          onClick={() => handleEdit(allergy)}
                          kind="ghost"
                        />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Column>
          <Column className="landing-page__content_r" lg={8} md={4} sm={4}>
            <img
              className="landing-page__illo"
              src="/images/tab-illo.png"
              alt="Carbon illustration"
            />
          </Column>
        </Grid>
      </Column>
    </Grid>
  );
};

export default InformationPage;
