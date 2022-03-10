import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Personel from './personel';
import PersonelDetail from './personel-detail';
import PersonelUpdate from './personel-update';
import PersonelDeleteDialog from './personel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonelDetail} />
      <ErrorBoundaryRoute path={match.url} component={Personel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PersonelDeleteDialog} />
  </>
);

export default Routes;
