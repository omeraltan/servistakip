import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServisIs from './servis-is';
import ServisIsDetail from './servis-is-detail';
import ServisIsUpdate from './servis-is-update';
import ServisIsDeleteDialog from './servis-is-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServisIsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServisIsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServisIsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServisIs} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServisIsDeleteDialog} />
  </>
);

export default Routes;
