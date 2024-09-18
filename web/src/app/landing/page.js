'use client';

import React from 'react';
import { Grid, Column, Button } from '@carbon/react';
import { ArrowRight } from '@carbon/icons-react';

export default function LandingPage() {
  return (
    <Grid fullWidth className="landing-grid">
      {/* 왼쪽 8칸: 배경색과 텍스트 */}
      <Column lg={8} md={4} sm={4} className="left-column">
        <div className="content-wrapper">
          {/* IBM 로고 */}
          <img
            src="/images/IBM_logo_wht.png" // 로고 이미지 경로
            alt="IBM Logo"
            className="logo"
          />

          {/* 헤더 텍스트 */}
          <h1 className="header-text">Open Healthcare Platform</h1>

          {/* 설명 텍스트 */}
          <p className="description-text">
            IBM’s <span className="bold-text">Open Healthcare Platform</span>{' '}
            enhances healthcare efficiency and patient safety by using
            blockchain technology for secure data sharing. It streamlines
            workflows, improves provider collaboration, and enables seamless
            access to digital health records for better care outcomes.
          </p>
          <Button
            className="landing-button"
            kind="primary"
            size="large-productive"
            renderIcon={ArrowRight}>
            I don't know Button
          </Button>
          <Button
            className="landing-button"
            kind="tertiary"
            size="large-productive"
            renderIcon={ArrowRight}>
            I know Button
          </Button>
        </div>
      </Column>

      {/* 오른쪽 8칸: 이미지 */}
      <Column lg={8} md={4} sm={4} className="right-column">
        <img
          src="/images/ohp-landing.png" // 이미지 경로
          alt="Healthcare"
          className="right-image"
        />
      </Column>
    </Grid>
  );
}
