import { Providers } from './providers';
import './globals.scss';

export const metadata = {
  title: 'IBM Open Healthcare',
  description: 'IBM OHP PROJECT ALLERGY UX/UI DEVELOPMENT-GROUP 48',
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head>
        <link rel="icon" href="/icons/pollen_2.ico" type="image/svg+xml" />
      </head>
      <body>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
