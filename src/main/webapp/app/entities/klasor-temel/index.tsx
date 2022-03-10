import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KlasorTemel from './klasor-temel';
import KlasorTemelDetail from './klasor-temel-detail';
import KlasorTemelUpdate from './klasor-temel-update';
import KlasorTemelDeleteDialog from './klasor-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KlasorTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KlasorTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KlasorTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={KlasorTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KlasorTemelDeleteDialog} />
  </>
);

export default Routes;
