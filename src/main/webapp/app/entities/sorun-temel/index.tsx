import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SorunTemel from './sorun-temel';
import SorunTemelDetail from './sorun-temel-detail';
import SorunTemelUpdate from './sorun-temel-update';
import SorunTemelDeleteDialog from './sorun-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SorunTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SorunTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SorunTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={SorunTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SorunTemelDeleteDialog} />
  </>
);

export default Routes;
