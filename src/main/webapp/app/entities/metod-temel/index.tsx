import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MetodTemel from './metod-temel';
import MetodTemelDetail from './metod-temel-detail';
import MetodTemelUpdate from './metod-temel-update';
import MetodTemelDeleteDialog from './metod-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MetodTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MetodTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MetodTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={MetodTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MetodTemelDeleteDialog} />
  </>
);

export default Routes;
