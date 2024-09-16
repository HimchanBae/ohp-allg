'use client';

import { Content, Theme } from '@carbon/react';
import BasicHeader from '@/components/BasicHeader/BasicHeader';
import BasicFooter from '@/components/BasicFooter/BasicFooter';

export function Providers({ children }) {
  return (
    <div className="layout">
      <Theme theme="g100">
        <BasicHeader />
      </Theme>
      <Content>{children}</Content>
      <Theme theme="g100">
        <BasicFooter />
      </Theme>
    </div>
  );
}
