import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServisIs from './servis-is';
import ServisTemel from './servis-temel';
import ProtokolTemel from './protokol-temel';
import IrtibatTemel from './irtibat-temel';
import MetodTemel from './metod-temel';
import ProjeTemel from './proje-temel';
import ServisProjeTemel from './servis-proje-temel';
import Personel from './personel';
import KurumTemel from './kurum-temel';
import SorunTemel from './sorun-temel';
import TabloTemel from './tablo-temel';
import KlasorTemel from './klasor-temel';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}servis-is`} component={ServisIs} />
      <ErrorBoundaryRoute path={`${match.url}servis-temel`} component={ServisTemel} />
      <ErrorBoundaryRoute path={`${match.url}protokol-temel`} component={ProtokolTemel} />
      <ErrorBoundaryRoute path={`${match.url}irtibat-temel`} component={IrtibatTemel} />
      <ErrorBoundaryRoute path={`${match.url}metod-temel`} component={MetodTemel} />
      <ErrorBoundaryRoute path={`${match.url}proje-temel`} component={ProjeTemel} />
      <ErrorBoundaryRoute path={`${match.url}servis-proje-temel`} component={ServisProjeTemel} />
      <ErrorBoundaryRoute path={`${match.url}personel`} component={Personel} />
      <ErrorBoundaryRoute path={`${match.url}kurum-temel`} component={KurumTemel} />
      <ErrorBoundaryRoute path={`${match.url}sorun-temel`} component={SorunTemel} />
      <ErrorBoundaryRoute path={`${match.url}tablo-temel`} component={TabloTemel} />
      <ErrorBoundaryRoute path={`${match.url}klasor-temel`} component={KlasorTemel} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
