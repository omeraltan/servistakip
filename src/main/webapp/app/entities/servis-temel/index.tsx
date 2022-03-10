import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServisTemel from './servis-temel';
import ServisTemelDetail from './servis-temel-detail';
import ServisTemelUpdate from './servis-temel-update';
import ServisTemelDeleteDialog from './servis-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServisTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServisTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServisTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServisTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServisTemelDeleteDialog} />
  </>
);

export default Routes;
