import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServisIsComponentsPage from './servis-is.page-object';
import ServisIsUpdatePage from './servis-is-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('ServisIs e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let servisIsComponentsPage: ServisIsComponentsPage;
  let servisIsUpdatePage: ServisIsUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    servisIsComponentsPage = new ServisIsComponentsPage();
    servisIsComponentsPage = await servisIsComponentsPage.goToPage(navBarPage);
  });

  it('should load Servises', async () => {
    expect(await servisIsComponentsPage.title.getText()).to.match(/Servises/);
    expect(await servisIsComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Servises', async () => {
    const beforeRecordsCount = (await isVisible(servisIsComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(servisIsComponentsPage.table);
    servisIsUpdatePage = await servisIsComponentsPage.goToCreateServisIs();
    await servisIsUpdatePage.enterData();
    expect(await isVisible(servisIsUpdatePage.saveButton)).to.be.false;

    expect(await servisIsComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(servisIsComponentsPage.table);
    await waitUntilCount(servisIsComponentsPage.records, beforeRecordsCount + 1);
    expect(await servisIsComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await servisIsComponentsPage.deleteServisIs();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(servisIsComponentsPage.records, beforeRecordsCount);
      expect(await servisIsComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(servisIsComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
